package api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;
import org.immutables.value.Value;

@Value.Immutable
@JsonSerialize(as = ImmutableSelectAction.class)
@JsonDeserialize(as = ImmutableSelectAction.class)
@SlackApiImmutableStyle
public abstract class SelectAction extends Action {

  public String getType() {
    return "select";
  }

  public abstract List<SelectOption> getOptions();

  @JsonProperty("selected_options")
  public abstract List<SelectOption> getSelectedOptions();

  public static ImmutableSelectAction.Builder builder() {
    return ImmutableSelectAction.builder();
  }

}
