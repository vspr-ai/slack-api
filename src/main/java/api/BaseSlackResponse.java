package api;

import java.util.Optional;

public abstract class BaseSlackResponse {

  public abstract String getOk();

  public abstract Optional<String> getError();
}
