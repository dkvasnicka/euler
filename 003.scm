(require-extension streams-math)
(require-extension streams-primitive)

; Having prime numbers as a stream is a very cool
; feature in the Chicken Scheme impl. of SRFI-41!
(define (find-largest-prime-factor n stream)
  (let* ((first-prime (stream-car stream))
         (rem (modulo n first-prime)))

   (if (= rem 0)
     (find-largest-prime-factor (/ n first-prime) prime-numbers-stream)
     (let ((next-prime-stream (stream-cdr stream)))
        (if (> (stream-car next-prime-stream) (sqrt n))
            n
            (find-largest-prime-factor n next-prime-stream))))))

(display
  (find-largest-prime-factor 600851475143 prime-numbers-stream))
