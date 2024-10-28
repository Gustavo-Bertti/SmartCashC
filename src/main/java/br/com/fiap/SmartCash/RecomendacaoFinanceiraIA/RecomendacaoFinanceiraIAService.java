package br.com.fiap.SmartCash.RecomendacaoFinanceiraIA;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.stereotype.Service;

@Service
public class RecomendacaoFinanceiraIAService {

    private final ChatClient chatClient;

    public RecomendacaoFinanceiraIAService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                .build();
    }

    public String sendChatMessage(StringBuilder message){
        return chatClient
                .prompt()
                .user(String.valueOf(message))
                .system("""
                        Você é gestor de finanças.
                        Você deve dar dicas de como melhorar os lucros.
                        Responda sempre de forma objetiva, pouco texto e de maneira eficiente.
                        Não responda listando tópicos.
                        Limite-se a falar sobre finanças.
                        """)
                .call()
                .content();
    }

}
