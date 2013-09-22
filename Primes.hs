module Primes where

-- Sieve of Eratosthenes
sieve :: [Int] -> [Int] -> Int -> [Int]
sieve (n:ns) d l = if n < l then sieve [x | x <- ns, mod x n /= 0] (d ++ [n]) l else d ++ [n] ++ ns
