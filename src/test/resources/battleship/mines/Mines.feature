Feature: Mines

  Scenario Outline: Mine moves
    Given Battlefield size <size> with <countMine> mines
    When Move mine
    Then Mine moves one step
    Examples:
      | size | countMine |
      | 8    |  1        |


  Scenario Outline: Mine does not go out of bounds
    Given Battlefield size <size> with mine <x> x <y> y <z> z
    When Move mine
    Then Mine moves one step
    Examples:
      | size |x  |y |z |
      | 8    | 0 |0 |0 |
      | 8    | 7 |0 |0 |
      | 8    | 0 |7 |0 |
      | 8    | 7 |7 |0 |
      | 8    | 7 |7 |7 |

  Scenario Outline: Mine does not crash into ship
    Given Battlefield size <size> with mine <x> x <y> y <z> z
    Given Add ship
    When Move mine
    Then Mine moves one step by y
    Examples:
      | size |x  |y |z |
      | 8    | 0 |0 |0 |