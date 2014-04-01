import Primes

-- A fn to find out the boundaries for nth prime number
primeBounds :: Int -> [Int]
primeBounds n = 
    map (\ x -> floor $ x * fromIntegral n) [lowerBoundary, upperBoundary]
        where
            upperBoundary = (log . fromIntegral) n + (log . log . fromIntegral) n
            lowerBoundary = upperBoundary - 1

problem7 :: Int -> [Int]
problem7 n = sieve [2..n] [] (floor . sqrt . fromIntegral $ n)    

main = print $ (problem7 (last $ primeBounds 10001)) !! 10000
