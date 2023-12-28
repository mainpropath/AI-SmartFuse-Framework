package com.ai.domain.chain;

import com.ai.interfaces.chain.Chain;
import com.ai.interfaces.chain.handler.ChainNodeHandler;
import com.ai.openAi.endPoint.images.ImageObject;
import com.ai.openai.handler.OpenaiImageCreateNodeHandler;
import com.ai.openai.handler.OpenaiTextDialogueNodeHandler;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * @Description: 简单Api调用链, 测试阶段
 **/
@Data
public class SimpleApiChain<Input, Output> implements Chain<Input, Output> {

    private final List<ChainNodeHandler> chainNodeList = new LinkedList<>();

    public static void main(String[] args) {
        SimpleApiChain<String, List<ImageObject>> chain = new SimpleApiChain<>();
        chain.chainNodeList.add(new OpenaiTextDialogueNodeHandler());
        chain.chainNodeList.add(new OpenaiImageCreateNodeHandler());
        List<ImageObject> run = chain.run("请描绘一个美丽的场景，50字以内");
        System.out.println(run);
    }

    @Override
    public Output run(Input input) {
        Object execute1 = chainNodeList.get(0).execute(input);
        System.out.println(execute1);
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Object execute2 = chainNodeList.get(1).execute(execute1);
        return (Output) execute2;
    }

}
