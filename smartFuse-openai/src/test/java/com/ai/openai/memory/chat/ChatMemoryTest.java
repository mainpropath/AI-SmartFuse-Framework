package com.ai.openai.memory.chat;

import com.ai.interfaces.message.ChatMessage;
import com.ai.openai.memory.chat.message.OpenaiAssistantMessage;
import com.ai.openai.memory.chat.message.OpenaiSystemMessage;
import com.ai.openai.memory.chat.message.OpenaiUserMessage;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试对话记录功能
 **/
public class ChatMemoryTest {

    /**
     * 测试单个信息记录功能
     */
    @Test
    public void test_memory_single() {
        // 新建一个记录器，不同的记录器的底层 ChatMemoryStore 可以是同一个。默认情况下底层自动新建一个 memoryStore 。
        // 即 recorder 和 memoryStore 关系可以是：一对多，多对一，多对多。
        OpenaiChatHistoryRecorder recorder = OpenaiChatHistoryRecorder.builder().id("1").maxMessageNumber(10).build();
        for (int i = 0; i < 15; i++) {
            if (i % 2 == 0) {
                // 如果添加的是系统消息，会进行去重判断
                recorder.add(OpenaiSystemMessage.builder().content(1 + " ").build());
            } else {
                recorder.add(OpenaiAssistantMessage.builder().content(i + " ").build());
            }
        }
        // 输出记录器当中的数据
        for (ChatMessage chatMessage : recorder.getCurrentMessages()) {
            System.out.println(chatMessage);
        }
    }

    /**
     * 测试多个记录器
     */
    @Test
    public void test_memory_multiple() {
        // 首先创建一个 memoryStore
        OpenaiChatMemoryStore openaiChatMemoryStore = new OpenaiChatMemoryStore();
        // 创建三个记录器，三个记录器使用同一个 memoryStore
        List<OpenaiChatHistoryRecorder> recorderList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            OpenaiChatHistoryRecorder recorder = OpenaiChatHistoryRecorder.builder().id(i + "")
                    .maxMessageNumber(10)
                    .memoryStore(openaiChatMemoryStore)
                    .build();
            recorderList.add(recorder);
        }
        // 向三个记录器当中添加数据
        for (int i = 0; i < 100; i++) {
            recorderList.get(i % 3).add(OpenaiUserMessage.builder().content(i % 3 + " ").build());
        }
        // 输出记录器当中的数据
        for (int i = 0; i < 3; i++) {
            OpenaiChatHistoryRecorder recorder = recorderList.get(i);
            for (ChatMessage chatMessage : recorder.getCurrentMessages()) {
                System.out.println("第" + (i + 1) + "个测试结果：" + chatMessage);
            }
        }
    }

}
