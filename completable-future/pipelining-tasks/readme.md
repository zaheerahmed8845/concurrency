Completable Future has the below methods:
-----------------------------------------

1. thenApply
   i. This will return back a value.
2. thenAccept
   i. This will return a Void.
3. thenCompose
   i. This will take the input into a Completable Future.
4. thenCombine
   i. This will combine the outputs from multiple futures.

All the above methods have an Async variant which will run in a separate threads and optionally takes an executor.