(load "package.lisp")
(in-package :euler)

(defun main ()
  (princ
    (iter (for d in-string (write-to-string (factorial 100)))
          (sum (parse-integer (make-string 1 :initial-element d))))))
