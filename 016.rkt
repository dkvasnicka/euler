#lang typed/racket/base

(require math/base
         racket/list)

; A version with custom-written multiplication. Slow as hell because 'append'
; on lists goes in linear time.
; Would be fast using (mutable?) constant-time access vectors.
(: *2 ((Listof Integer) -> (Listof Integer)))

(define [*2 n]
  (let: loop [[result : (Listof Integer) '()]
              [num n]
              [carryover 0]]
    (if (and (empty? num) (= carryover 0))
        result
        (if (empty? num)
          (append result (list carryover))
          (let-values [[[q r] (quotient/remainder (* 2 (car num)) 10)]]
                      (loop
                        (append result (list (+ carryover r)))
                        (cdr num)
                        q))))))

(: two-to-the-power-of (Integer -> (Listof Integer)))

(define [two-to-the-power-of e]
  (if (= e 1)
      '(2)
      (*2 (two-to-the-power-of (sub1 e)))))

(time (sum (two-to-the-power-of 1000)))

; Freaking fast version using built-in expt function. Can go in less than 1 ms on a 3 yrs old MBP
[: number->digits (Integer -> (Listof Integer))]

(define [number->digits n] 
  (if (< n 10)
      (list n)
      (cons (remainder n 10) (number->digits (quotient n 10)))))

(time (sum (number->digits (expt 2 1000))))
