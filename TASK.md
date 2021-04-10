# [Software Sauna](https://www.softwaresauna.com/) Code Challenge

- Path following algorithm in ASCII Map
- Find the position of character `@`
- Follow the path, stop when character `x` is reached

## Code Challenge

Write a piece of code that takes ASCII map as an input
and outputs the collected letters and the list of characters
of the travelled path.

- Input:
    - ASCII map (hard-coded, in a file,
      copied from a magic scroll - your choice)
- Output:
    - Collected letters
    - Path as characters

What we are looking for in the solution:

- clean code
    - small methods/functions/classes
    - good naming
    - minimise code duplication
- tests
    - mandatory: acceptance tests with pasted examples from below
    - bonus: microtests - small bits of logic should be tested separately (possible if clean code principles are observed, see above) ...
      for more on microtests see
      [this article](https://www.geepawhill.org/2020/06/12/microtest-tdd-more-definition)

## Valid maps

### Map 1 - a basic example

```
  @---A---+
          |
  x-B-+   C
      |   |
      +---+
```

Expected result:
- Letters ```ACB```
- Path as characters ```@---A---+|C|+---+|+-B-x```

### Map 2 - go straight through intersections

```
  @
  | +-C--+
  A |    |
  +---B--+
    |      x
    |      |
    +---D--+
```

Expected result:
- Letters ```ABCD```
- Path as characters ```@|A+---B--+|+--C-+|-||+---D--+|x```

### Map 3 - letters may be found on turns

```
  @---A---+
          |
  x-B-+   |
      |   |
      +---C
```

Expected result:
- Letters ```ACB```
- Path as characters ```@---A---+|||C---+|+-B-x```

### Map 4 - do not collect letters twice

```
    +--B--+
    |   +-C-+
 @--A-+ | | |
    | | +-+ D
    +-+     |
            x
```

Expected result:
- Letters ```ABCD``` (*not* `AABCCD`)
- Path as characters ```@--A-+|+-+|A|+--B--+C|+-+|+-C-+|D|x```

### Map 5 - keep direction, even in a compact space

```
 +-B-+
 |  +C-+
@A+ ++ D
 ++    x
```

Expected result:
- Letters ```ABCD```
- Path as characters ```@A+++A|+-B-+C+++C-+Dx```

## Invalid maps:

### Map 6 - no start

```
     -A---+
          |
  x-B-+   C
      |   |
      +---+
```

Expected result: Error

### Map 7 - no end

```
   @--A---+
          |
    B-+   C
      |   |
      +---+
```

Expected result: Error

### Map 8 - multiple starts

```
   @--A-@-+
          |
  x-B-+   C
      |   |
      +---+
```

Expected result: Error

### Map 9 - multiple ends

```
   @--A---+
          |
  x-Bx+   C
      |   |
      +---+
```

Expected result: Error

### Map 10 - T forks

```
        x-B
          |
   @--A---+
          |
     x+   C
      |   |
      +---+
```

Expected result: Error
