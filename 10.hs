import Primes

problem10 :: Int -> [Int]
problem10 n = sieve [2..n] [] (floor . sqrt . fromIntegral $ n)    

main = print $ sum (problem10 2000000)
