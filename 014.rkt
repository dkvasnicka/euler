#lang racket

(require racket/unsafe/ops)

(define + unsafe-fx+)
(define - unsafe-fx-)
(define * unsafe-fx*)
(define / unsafe-fxquotient)
(define = unsafe-fx=)
(define > unsafe-fx>)

(define/match [populate n]
  [[(? odd?)]  (+ 1 (* 3 n))]
  [[(? even?)] (/ n 2)])

(define [collatz-length n]
  (let loop [[x   n]
             [cnt 1]]
    (if (= 1 x)
        cnt
        (loop (populate x) (+ 1 cnt)))))

(define [p14 n maxlen result]
  (if (= 1 n)
      result
      (let* [[curlen (collatz-length n)]
             [better? (> curlen maxlen)]]
        (p14 (- n 1)
             (if better? curlen maxlen)
             (if better? n result)))))

(time (p14 999999 0 0))
