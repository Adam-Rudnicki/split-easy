# split-easy
Application to settle bill between users.

* ### MVVM
* ### RoomDB
* ### JetpackCompose
* ### Approach with UseCases Repositories and DAO
* ### Basic approach with dependecy injection (DI) using Dagger Hilth
* ### Usage of Flow and Corutines 
* ### Mapping between Entities and Domain Model
* ### Settling bill algorithm
  Algorithm sorts the users' contributions from the bill by the value of the difference (actual amount paid - amount to pay). Then, the algorithm pairs the user with the largest underpayment with the user with the      largest overpayment and assigns how much the user wiuth underpayment should transfer. This step is repeated until the balancing process is completed. This simple approach ensures an optimal balance between            algorithmic complexity, optimal number of money transfers and the minimization of unnecessary transfers (it is illogical for individuals who have overpaid to still need to make transfers). To settle the bill, the     sum of all actual amounts paid and the sum of amounts to pay must be equal.

## Data Base Structure
<img src="https://github.com/Adam-Rudnicki/split-easy/assets/95653349/2d51f2d0-5f7a-4322-aeaf-70f48f1ce721" width="1000">

## App UI overview
<img src="https://github.com/Adam-Rudnicki/split-easy/assets/95653349/e5ccbc61-a381-4e97-8b9b-acc245b1325e" width="350">
<img src="https://github.com/Adam-Rudnicki/split-easy/assets/95653349/fe0dce1e-21aa-4d93-81d0-1e63aa97ec3c" width="350">
<img src="https://github.com/Adam-Rudnicki/split-easy/assets/95653349/876b8cbc-4b75-4c09-80fd-6a1c946b63c3" width="350">
<img src="https://github.com/Adam-Rudnicki/split-easy/assets/95653349/f199b818-aaf4-4c76-a152-93a57760b1ae" width="350">
<img src="https://github.com/Adam-Rudnicki/split-easy/assets/95653349/7797d2f9-7818-4559-83c1-960c72783336" width="350">
<img src="https://github.com/Adam-Rudnicki/split-easy/assets/95653349/55100278-fafa-4925-a6ef-44f43f08b344" width="350">
<img src="https://github.com/Adam-Rudnicki/split-easy/assets/95653349/3b53fde1-8949-451e-b560-171725414845" width="350">
