#lang racket

(require srfi/13)

(define [alpha-position word]
        (for/sum [[c (in-string word)]]
                 (- (char->integer c) 64)))

(let* [[names (string-split (string-delete #\" (port->string)) ",")]
       [sorted (sort names string<?)]]
      (for/sum [[i (in-naturals 1)]
                [wvalue (sequence-map alpha-position sorted)]]
               (* i wvalue)))
