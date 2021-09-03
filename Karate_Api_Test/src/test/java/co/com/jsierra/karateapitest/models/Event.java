package co.com.jsierra.karateapitest.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event{
    private String application;
    private String type;
    private String severity;
    private String message;
}
