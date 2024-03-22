package com.ai.domain.data.images;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Image {

    private String url;

    private String b64Json;

    public static Image b64Json(String b64Json) {
        return new Image(null, b64Json);
    }

    public static Image url(String url) {
        return new Image(url, null);
    }

    public static Image from(String url, String b64Json) {
        return new Image(url, b64Json);
    }

}
