#lang racket/base

(require math/number-theory
         racket/sequence
         math/base)

(define [proper-divisors-sum n]
        (- (sum (divisors n)) n))

(for/sum [[i (in-range 2 10001)]]
         (let [[div-sum (proper-divisors-sum i)]]
           (if (and (< i div-sum 10000)
                    (= i (proper-divisors-sum div-sum)))
             (+ i div-sum) 0)))
