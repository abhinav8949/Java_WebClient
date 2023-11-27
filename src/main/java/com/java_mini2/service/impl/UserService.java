package com.java_mini2.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.java_mini2.exception.UserCustomException;
import com.java_mini2.model.entity.User;
import com.java_mini2.model.response.BackendResponse;
import com.java_mini2.model.response.PageInfo;
import com.java_mini2.repository.UserRepository;
import com.java_mini2.service.IUserService;
import com.java_mini2.validation.EnglishAlphabetValidator;
import com.java_mini2.validation.NumericValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.java_mini2.constants.UserConstant.*;

@Service
@Slf4j
public class UserService implements IUserService {
    private final WebClient api1WebClient;
    private final WebClient api2WebClient;
    private final WebClient api3WebClient;
    private final UserRepository userRepository;

    public UserService(@Qualifier("api1WebClient") WebClient api1WebClient,
                       @Qualifier("api2WebClient") WebClient api2WebClient,
                       @Qualifier("api3WebClient") WebClient api3WebClient,
                       UserRepository userRepository) {
        this.api1WebClient = api1WebClient;
        this.api2WebClient = api2WebClient;
        this.api3WebClient = api3WebClient;
        this.userRepository = userRepository;
    }

    private CompletableFuture<List<User>> fetchRandomUserAsync() {
        return CompletableFuture.supplyAsync(() -> {
            String api1Response = api1WebClient.get()
                    .uri("/api/")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            List<User> users = new ArrayList<>();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode api1Data;
            try {
                api1Data = objectMapper.readValue(api1Response, JsonNode.class);
                if (api1Data.has("results") && api1Data.get("results").isArray()) {
                    ArrayNode resultsArray = (ArrayNode) api1Data.get("results");
                    for (JsonNode resultNode : resultsArray) {
                        User user = new User();
                        user.setName(getFullName(resultNode));
                        user.setFirstName(getFirstName(resultNode));
                        user.setAge(getAge(resultNode));
                        user.setGender(getGender(resultNode));
                        user.setDob(getDob(resultNode));
                        user.setNationality(getNationality(resultNode));
                        users.add(user);
                    }
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e.getMessage());
            }
            return users;
        });
    }

    private String getFullName(JsonNode resultNode) {
        String firstName = resultNode.path("name").path("first").asText();
        String lastName = resultNode.path("name").path("last").asText();
        return firstName + " " + lastName;
    }

    private String getFirstName(JsonNode resultNode) {
        return resultNode.path("name").path("first").asText();
    }

    private int getAge(JsonNode resultNode) {
        return resultNode.path("dob").path("age").asInt();
    }

    private String getGender(JsonNode resultNode) {
        return resultNode.path("gender").asText();
    }

    private String getDob(JsonNode resultNode) {
        return resultNode.path("dob").path("date").asText();
    }

    private String getNationality(JsonNode resultNode) {
        return resultNode.path("nat").asText();
    }

    private CompletableFuture<Boolean> checkNationalityAsync(User user) {
        return CompletableFuture.supplyAsync(() -> {
            String api2Response = api2WebClient.get()
                    .uri("/?name=" + user.getFirstName())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode api2Data;
            try {
                api2Data = objectMapper.readValue(api2Response, JsonNode.class);
                JsonNode node = api2Data.get("country");
                for (JsonNode countryNode : node) {
                    String countryId = countryNode.get("country_id").asText();
                    if (countryId.equalsIgnoreCase(user.getNationality())) {
                        return true;
                    }
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e.getMessage());
            }
            return false;
        });
    }

    private CompletableFuture<Boolean> checkGenderAsync(User user) {
        return CompletableFuture.supplyAsync(() -> {
            String api3Response = api3WebClient.get()
                    .uri("/?name=" + user.getFirstName())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode api3Data;
            try {
                api3Data = objectMapper.readValue(api3Response, JsonNode.class);
                String gender = api3Data.get("gender").asText();
                return gender.equals(user.getGender());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e.getMessage());
            }
        });
    }

    @Override
    public List<User> createUser(int size) throws UserCustomException {
        NumericValidator.getInstance().validateNumericLimit(size);

        List<User> savedUsers = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            try {
                List<User> randomUsers = fetchRandomUserAsync().get();
                if (randomUsers.isEmpty()) {
                    throw new UserCustomException(API_FAILURE);
                }
                // Mark users as VERIFIED or TO_BE_VERIFIED based on Step 4 criteria
                for (User user : randomUsers) {
                    CompletableFuture<Boolean> nationalityCheckFuture = checkNationalityAsync(user);
                    CompletableFuture<Boolean> genderCheckFuture = checkGenderAsync(user);

                    Boolean nationalityMatch = nationalityCheckFuture.join();
                    Boolean genderMatch = genderCheckFuture.join();

                    if (nationalityMatch && genderMatch) {
                        user.setVerificationStatus(VERIFIED);
                    } else {
                        user.setVerificationStatus(TO_BE_VERIFIED);
                    }

                    LocalDateTime now = LocalDateTime.now();
                    user.setDateCreated(now);
                    user.setDateModified(now);
                }
                userRepository.saveAll(randomUsers);
                log.info("User is saved in database.");
                savedUsers.addAll(randomUsers);
            } catch (Exception e) {
                throw new UserCustomException(e.getMessage());
            }
        }
        log.info("Saved User: {}", savedUsers);
        return savedUsers;
    }

    @Override
    public BackendResponse getUsers(String sortType, String sortOrder, int limit, int offset) throws UserCustomException {
        validateParametre(sortType, sortOrder, limit, offset);
        PageInfo pageInfo = new PageInfo();
        Pageable pageable = PageRequest.of(offset, limit);
        Page<User> resultPage = getResultPage(sortType, sortOrder, pageable);
        pageInfo.setHasNextPage(resultPage.hasNext());
        pageInfo.setHasPreviousPage(resultPage.hasPrevious());
        pageInfo.setTotal(resultPage.getTotalElements());
        return BackendResponse.builder()
                .data(resultPage.getContent())
                .pageInfo(pageInfo)
                .build();
    }

    private Page<User> getResultPage(String sortType, String sortOrder, Pageable pageable) throws UserCustomException {
        if (SORT_BY_NAME.equalsIgnoreCase(sortType)) {
            return userRepository.findAllByNameOrder(SORT_ORDER_EVEN.equalsIgnoreCase(sortOrder) ? 0 : 1, pageable);
        } else if (SORT_BY_AGE.equalsIgnoreCase(sortType)) {
            return userRepository.findAllByAgeOrder(SORT_ORDER_EVEN.equalsIgnoreCase(sortOrder) ? 0 : 1, pageable);
        } else {
            throw new UserCustomException(SOMETHING_WENT_WRONG);
        }
    }

    private void validateParametre(String sortType, String sortOrder, int limit, int offset) throws UserCustomException {
        EnglishAlphabetValidator englishAlphabetValidator = EnglishAlphabetValidator.getInstance();
        englishAlphabetValidator.validateAlphabet(sortType);
        englishAlphabetValidator.validateAlphabet(sortOrder);

        NumericValidator numericValidator = NumericValidator.getInstance();
        numericValidator.validateNumericLimit(limit);
        numericValidator.validateNumericOffset(offset);
    }
}
