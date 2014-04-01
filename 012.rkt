#lang racket

(require math/number-theory
         srfi/41)

; A lazy stream of triangle numbers 
(define [triangle-nums count]
  (stream-take count 
               (stream-cdr
                 (stream-scan + 0 (stream-from 1)))))

(stream-car 
  (stream-drop-while 
    (λ [n] (<= (foldl * 1 (map (λ [f] (+ 1 (cadr f))) 
                               (factorize n))) 500))
    (triangle-nums 50000)))
