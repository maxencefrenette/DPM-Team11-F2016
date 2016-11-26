package ca.mcgill.ecse211.team11.pathfinding;

/**
 * Enumerates the different possible stated of a grid square.
 * 
 * @author Maxence Frenette
 * @since 2.0
 * @since 2.0
 */
public enum InternalGridCell {
  UNKNOWN {
    @Override
    public String toString() {
      return "?";
    }
  },
  EMPTY {
    @Override
    public String toString() {
      return " ";
    }
  },
  UNKNOWN_BLOCK {
    @Override
    public String toString() {
      return "U";
    }
  },
  STYROFOAM_BLOCK {
    @Override
    public String toString() {
      return "B";
    }
  },
  WOODEN_BLOCK {
    @Override
    public String toString() {
      return "W";
    }
  },
  RED_ZONE {
    @Override
    public String toString() {
      return "R";
    }
  },
  GREEN_ZONE {
    @Override
    public String toString() {
      return "G";
    }
  },
  NO_ENTRY {
    @Override
    public String toString() {
      return "X";
    }
  }
}
