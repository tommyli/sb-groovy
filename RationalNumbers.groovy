import groovy.transform.Canonical

static Long product(Long... nums) {
  if (nums.size() == 0) {
    0
  }
  else if (nums.size() == 1) {
    nums[0]
  }
  else {
    product(nums.head() * nums.tail().head(), *(nums.tail().tail()))
  }
}

static def intersectAll(lists) {
  intersectAllRecursive(lists.head(), lists.tail())
}

static def intersectAllRecursive(intersection, lists) {
  if (lists.empty) {
    return intersection
  }
  else {
    return intersectAllRecursive(intersection.intersect(lists.head()), lists.tail())
  }
}

static Collection<Long> factors(Long num) {
  if (num == 0) {
    return []
  }
  def lowFactors = (1..((long) Math.sqrt(num))).findAll {num % it == 0}

  lowFactors.collect {[it, (long)(num / it)]}.flatten().unique()
}

// Highest common factor
static Long hcf(Long... nums) {
  if (nums.any { it == 0 }) { return 0 }
  intersectAll(nums.collect { factors(it) }).max()
}

// Lowest common multiple
static Long lcm(Long... nums) {
  Collection<Long> hcmFactors = factors(product(nums))
  hcmFactors.findAll { Long factor ->
    nums.every { Long num -> (factor % num) == 0 }
  }.min() ?: 0
}

@Canonical
class Rational {
  Long n, d = 1

  static Rational ZERO = new Rational(0, 0)
  static Rational ONE = new Rational(1, 1)

  Rational(Long n, Long d) {
    // if (d == 0) {throw new IllegalArgumentException('Denominator cannot be 0')}
    this.n = n
    this.d = d
  }

  Rational absolute() {
    new Rational(Math.abs(n), Math.abs(d))
  }

  Rational negative() {
    new Rational(-1 * n, d)
  }

  Rational plus(Rational addend) {
    if ((addend ?: ZERO) == ZERO) return this
    if (this == ZERO) return addend

    Long nResult = this.n * addend.d + this.d * addend.n
    Long dResult = this.d * addend.d
    new Rational(nResult, dResult).simplify()
  }

  Rational minus(Rational subtrahend) {
    if ((subtrahend ?: ZERO) == ZERO) return this
    if (this == ZERO) return subtrahend.negative()

    Long nResult = this.n * subtrahend.d - this.d * subtrahend.n
    Long dResult = this.d * subtrahend.d
    new Rational(nResult, dResult).simplify()
  }

  Rational multiply(Rational factor) {
    if (this == ZERO || (factor ?: ZERO) == ZERO) return ZERO

    Long nResult = this.n * factor.n
    Long dResult = this.d * factor.d
    new Rational(nResult, dResult).simplify()
  }

  Rational div(Rational divisor) {
    assert divisor != ZERO, 'Divide by zero error'
    if (this == ZERO) return ZERO

    Long nResult = this.n * divisor.d
    Long dResult = this.d * divisor.n
    assert dResult != 0, 'Divide by zero error'
    new Rational(nResult, dResult).simplify()
  }

  Rational power(Long exponent) {
    Long nResult = 1
    Long dResult = 1

    if (exponent == 0) {
      return ONE
    }
    else if (exponent > 0) {
      nResult = n ** exponent
      dResult = d ** exponent
    }
    else {
      nResult = d ** exponent.abs()
      dResult = n ** exponent.abs()
    }

    new Rational(nResult, dResult).simplify()
  }

  Rational simplify() {
    Long highFactor = RationalNumbers.hcf(n, d)
    new Rational((n / highFactor) as Long, (d / highFactor) as Long)
  }
}

assert product(2, 4, 6) == 48
assert product(24, 16, 0) == 0
assert factors(0).sort() == []
assert factors(2).sort() == [1, 2]
assert factors(17).sort() == [1, 17]
assert factors(24).sort() == [1, 2, 3, 4, 6, 8, 12, 24]
assert intersectAll([ [1, 2, 3], [2, 3, 4], [3, 4, 5] ]) == [3]
assert hcf(0) == 0
assert hcf(0, 1) == 0
assert hcf(0, 0) == 0
assert hcf(24, 24) == 24
assert hcf(24, 24, 24) == 24
assert hcf(6, 12, 18) == 6
assert hcf(48, 96, 18) == 6
assert hcf(24, 18) == hcf(18, 24)
assert lcm(0) == 0
assert lcm(2) == 2
assert lcm(2, 4, 6) == 12
assert lcm(3, 6, 9) == 18
assert lcm(11, 7, 13) == 11 * 7 * 13

assert new Rational(1, 2) == new Rational(1, 2).absolute()
assert new Rational(1, 2) + new Rational(2, 3) == new Rational(7, 6)
assert new Rational(4, 2) + new Rational(4, 2) == new Rational(4, 1)
assert new Rational(6, 2) - new Rational(1, 2) == new Rational(5, 2)
assert new Rational(6, 2) - new Rational(-1, 2) == new Rational(7, 2)
assert new Rational(2, 4) * new Rational(3, 6) == new Rational(1, 4)
assert new Rational(-1, 2) * new Rational(-2, 3) == new Rational(1, 3)
assert new Rational(-1, 2) * new Rational(2, 3) == new Rational(-1, 3)
assert new Rational(8, 2) / new Rational(2, 4) == new Rational(8, 1)
// assert new Rational(1, 2) / new Rational(-2, 3) == new Rational(-3, 4)
assert new Rational(1, 2) ** 0 == new Rational(1, 1)
assert new Rational(1, 2) ** 2 == new Rational(1, 4)
assert new Rational(1, 2) ** -2 == new Rational(4, 1)
