#lang typed/racket/base

(require math/base)

[: number->digits (Integer -> (Listof Integer))]

(define [number->digits n] 
  (if (< n 10)
      (list n)
      (cons (remainder n 10) (number->digits (quotient n 10)))))

[: sum-doubled-digits (Integer -> Integer)]

(define [sum-doubled-digits n]
  (foldl (lambda: [(d : Integer) (accum : Integer)]
                  (displayln d)
                  (+ accum 
                     (let-values [[[l r] (quotient/remainder (* 2 d) 10)]]
                                 (+ l r)))) 
         0 (number->digits n)))

[: power-of-two-digit-sum (Integer -> Integer)]

(define [power-of-two-digit-sum e]
  (if (= e 1)
      2
      (sum-doubled-digits (power-of-two-digit-sum (sub1 e)))))

(time (power-of-two-digit-sum 6))

; double just the number; sum in the end
