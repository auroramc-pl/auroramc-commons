package pl.auroramc.commons.bukkit.integration;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import org.bukkit.Server;

public class ExternalIntegrator {

  private final Map<Predicate<Server>, ExternalIntegration> integrations;

  public ExternalIntegrator(final Map<Predicate<Server>, ExternalIntegration> integrations) {
    this.integrations = integrations;
  }

  public void configure(final Server server) {
    for (final Entry<Predicate<Server>, ExternalIntegration> entry : integrations.entrySet()) {
      if (entry.getKey().test(server)) {
        entry.getValue().configure();
      }
    }
  }
}
