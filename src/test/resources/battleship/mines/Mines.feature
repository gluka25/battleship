Feature: Mines

  Scenario Outline: Mine moves
    Given Battlefield size <size> with <countMine> mines
    When Move mine
    Then Mine moves one step
    Examples:
      | size | countMine |
      | 8    |  1        |
