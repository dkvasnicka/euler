#lang racket/base

(require racket/port
         racket/string
         racket/function)

(define [load-and-parse-data f]
  (call-with-input-file f 
     (λ [i] (map (compose (curry map string->number) string-split) 
                 (port->lines i)))))

(define [collapse-lines lower upper]
  (let loop [[l lower]
             [u upper]
             [out '()]]
    (if (null? u)
        (reverse out)
        (loop (cdr l)
              (cdr u)
              (cons (+ (car u) (max (car l) (cadr l))) 
                    out)))))

(define [problem18 triangle]
  (if (= 1 (length triangle))
      (caar triangle)
      (let [[tail (cdr triangle)]]
         (problem18 (cons (collapse-lines (car triangle)
                                          (car tail))
                          (cdr tail))))))

(problem18 '((8 5 9 3) 
             (2 4 6) 
             (7 4) 
             (3))) ; => 23

(problem18 (load-and-parse-data "018.txt"))
(problem18 (load-and-parse-data "067.txt"))
