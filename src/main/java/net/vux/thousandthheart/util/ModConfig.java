package net.vux.thousandthheart.util;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;

public class ModConfig extends WrappedConfig {
  @Comment("Minimum heart value that you can reach. 2 points = 1 heart icon")
  public int minHeartPoints = 10;
  @Comment("Maximum heart value that you can reach. 2 points = 1 heart icon")
  public int maxHeartPoints = 40;
  @Comment("The range (in blocks) that heart lantern can give Regeneration effect.")
  public int heartLanternRange = 2;
}
