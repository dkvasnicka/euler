#lang racket

(require math/base)

(substring 
  (number->string
    (sum (map string->number
              (port->lines (open-input-file "013.txt"))))) 0 10)
