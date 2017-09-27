package cs3500.music.model;

/**
 * Represents 12 distinct pitches, each pitch is represented by a number.
 * The pitches are C,C#,D,D#,E,F,F#,G,G#,A,A#,B.
 * They are listed from low to high.
 */
public enum Pitch {
  C(0), CSharp(1), D(2), DSharp(3), E(4), F(5), FSharp(6), G(7), GSharp(8), A(9), ASharp(10), B(11);

  //field of the pitch
  private int num;

  /**
   * This method constructs the pitch.
   *
   * @param num number represents the pitch
   */
  Pitch(int num) {
    this.num = num;
  }

  /**
   * This method gets the Pitch based on the input string.
   *
   * @param pitch The input pitch string.
   * @return Pitch corresponding to the string.
   * @throws IllegalArgumentException if there is no such pitch
   */
  public static Pitch toPitch(String pitch) throws IllegalArgumentException {
    switch (pitch) {
      case "C":
        return C;
      case "C#":
        return CSharp;
      case "D":
        return D;
      case "D#":
        return DSharp;
      case "E":
        return E;
      case "F":
        return F;
      case "F#":
        return FSharp;
      case "G":
        return G;
      case "G#":
        return GSharp;
      case "A":
        return A;
      case "A#":
        return ASharp;
      case "B":
        return B;
      default:
        throw new IllegalArgumentException("Please enter a valid pitch string!");
    }
  }

  @Override
  public String toString() {
    switch (num) {
      case 0:
        return "C";
      case 1:
        return "C#";
      case 2:
        return "D";
      case 3:
        return "D#";
      case 4:
        return "E";
      case 5:
        return "F";
      case 6:
        return "F#";
      case 7:
        return "G";
      case 8:
        return "G#";
      case 9:
        return "A";
      case 10:
        return "A#";
      case 11:
        return "B";
      default:
        throw new IllegalArgumentException("Please enter a valid pitch number!");
    }
  }

  /**
   * This method outputs the sequence number of this pitch.
   *
   * @return The sequence number of this pitch.
   */
  public int getNumber() {
    return num;
  }
}
