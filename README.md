# 🤖 ChatGPT Spring Boot Integration

This is a Spring Boot application that integrates with OpenAI's ChatGPT API (`https://api.openai.com/v1/chat/completions`). It exposes a REST endpoint to send prompts and receive responses, with Swagger UI for easy API testing.

---

## 👨‍💻 Developer
    Roshan Jadhav

Follow me on:  
💼 [LinkedIn](https://www.linkedin.com/in/roshan-jadhav-1a5384174/)  
🐙 [GitHub](https://github.com/roshanJadhav77)


## 1. ✅ Requirements

    - Java 17 or higher
    - Maven 3.6+
    - OpenAI API Key (Get yours at https://platform.openai.com/api-keys)
    - Internet connection to call OpenAI API

----

## 📁 Project Structure

    chatgpt-bot/
    ├── src/
    │ ├── main/
    │ │ ├── java/com/chatgpt/chatgpt_bot/
    │ │ │ ├── controller/ChatController.java
    │ │ │ ├── dto/ChatRequest.java, ChatResponse.java
    │ │ │ ├── service/ChatService.java
    │ │ │ └── config/SwaggerConfig.java
    │ └── resources/
    │ └── application.properties
    └── pom.xml

##  2. 🛠️ Build the project
    mvn clean install

##  3. ▶️ Run the Application
    mvn spring-boot:run
The application will start on http://localhost:8080

## 4. 📑 API Documentation and Testing with Swagger UI
Swagger UI is integrated and available at:

    http://localhost:8080/swagger-ui/index.html

Use this UI to test the API endpoint easily without any external tools.

## 5. 📑 Postman curl 
    curl --location 'http://localhost:8080/api/chat' \
    --header 'Content-Type: application/json' \
    --data '{
    "prompt" : "WHo is PM of India"
    }'

## 🔗 API Endpoint
    POST /api/chat

## 📝 Sample Request Body:
    {
        "prompt" : "Who is PM of India"
    }

## 📝 Sample Response:
    {
        "reply": "The Prime Minister of India is Narendra Modi. He has been in office since May 26, 2014. 
                 Please verify with current sources to ensure this information is still accurate, as political positions can change."
    }



## 🤖 ChatGPT API Integration Details
    Uses OpenAI's Chat Completion API: /v1/chat/completions
    
    Sends user prompt in message format
    
    Uses RestTemplate for HTTP communication
    
    Parses the response to extract ChatGPT's answer
    
    Supports dynamic model configuration (gpt-4o-mini, gpt-3.5-turbo, etc.)
