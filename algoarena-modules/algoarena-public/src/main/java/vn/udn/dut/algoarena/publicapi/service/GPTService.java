package vn.udn.dut.algoarena.publicapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import vn.udn.dut.algoarena.publicapi.dto.Message;
import vn.udn.dut.algoarena.publicapi.request.GPTRequest;
import vn.udn.dut.algoarena.publicapi.response.GPTResponse;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GPTService {

    private final String API_KEY = "test";
    private final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    public String getGPTResponse(String userInput) {
        int maxRetries = 3; // Maximum retry attempts
        int retryCount = 0;
        long backoffTime = 1000; // Initial backoff time in milliseconds

        while (retryCount < maxRetries) {
            try {
                // 1. Create RestTemplate
                RestTemplate restTemplate = new RestTemplate();

                // 2. Create message
                Message userMessage = new Message("user", userInput);
                GPTRequest gptRequest = new GPTRequest(
                        "gpt-3.5-turbo", // Model GPT-4
                        List.of(userMessage),
                        0.7 // Temperature
                );

                // 3. Create RequestEntity
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setBearerAuth(API_KEY);

                HttpEntity<GPTRequest> requestEntity = new HttpEntity<>(gptRequest, headers);

                // 4. Send POST request
                ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                        OPENAI_API_URL, requestEntity, String.class
                );

                // 5. Parse JSON response
                ObjectMapper objectMapper = new ObjectMapper();
                GPTResponse gptResponse = objectMapper.readValue(
                        responseEntity.getBody(),
                        GPTResponse.class
                );

                return gptResponse.getChoices().get(0).getMessage().getContent();
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                    // 429 Too Many Requests error, apply backoff
                    retryCount++;
                    if (retryCount >= maxRetries) {
                        return "API request quota exceeded, please try again later.";
                    }
                    try {
                        // Wait before retrying
                        Thread.sleep(backoffTime);
                        backoffTime *= 2; // Exponential backoff
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        return "Error while handling request.";
                    }
                } else {
                    // Handle other errors
                    return "Error while communicating with GPT-4 API.";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "Error while communicating with GPT-4 API.";
            }
        }

        return "Error while communicating with GPT-4 API after retries.";
    }
}
