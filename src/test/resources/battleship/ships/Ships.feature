Feature: Ships

  Scenario Outline: Moving ships moves
    Given Battlefield size <size> with <countMine> mines and <countShips> moving ships
    When Move moving ship
    Then Moving ships moves one step
    Examples:
      | size | countMine | countShips |
      | 8    | 0         | 1          |

  Scenario Outline: Moving ship does not go out of bounds
    Given Battlefield size <size> with moving ship <x> x <y> y <z> z
    When Move moving ship
    Then Moving ships moves one step
    Examples:
      | size |x  |y |z |
      | 8    | 0 |0 |0 |
      | 8    | 7 |0 |0 |
      | 8    | 0 |7 |0 |
      | 8    | 7 |7 |0 |
      | 8    | 7 |7 |7 |

  Scenario Outline: Ships do not move
    Given Battlefield size <size> with <countMine> mines and <countShips> ships
    When Move battleField items
    Then Ships do not move
    Examples:
      | size | countMine | countShips |
      | 8    | 0         | 1          |

  Scenario Outline: Moving ships are crashed
    Given Battlefield size <size> with <countMine> mines near <countShips> moving ships
    When Move battleField items
    Then Ships are crashed
    Examples:
      | size | countMine | countShips |
      | 8    | 1         | 1          |

  Scenario Outline: Ships are crashed
    Given Battlefield size <size> with <countMine> mines near <countShips> ships
    When Move battleField items
    Then Ships are crashed
    Examples:
      | size | countMine | countShips | sizeShips|
      | 8    | 1         | 1          | 1        |

  Scenario Outline: Moving ship becomes ship
    Given Battlefield size <size> with <countMine> mines near <countShips> moving ships
    When Move battleField items
    Then Moving ship becomes ship
    Examples:
      | size | countMine | countShips | sizeShips|
      | 8    | 1         | 1          | 1        |