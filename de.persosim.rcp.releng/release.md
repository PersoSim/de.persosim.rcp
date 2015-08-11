Release de.persosim.rcp
=======================
This document describes the steps necessary to create a product release.

Prepare and build the release
-----------------------------
All required steps for preparation and build of a releasable product are already covered by the overall release process description which can be found in [../../com.hjp.releng/com.hjp.releng/doc/release.md].

Test it
-------
1. [ ] __Run PersoSim__  
Run PersoSim and check the following
 - [ ] GUI started
 - [ ] About dialog reflects correct information (e.g. version number)
 - [ ] no errors reported in the console

1. [ ] __Simulate certification test__  
Start PersoSim and load a GT_Personalization from file. 
Run applicable tests from GlobalTester Prove ePA.
 - [ ] Layer 6
 - [ ] Layer 7

1. [ ] __Online authentication__  
Start PersoSim and select the first provided personalization. 
Execute an online authentication against an eID-Server in the TestPKI.

1. [ ] __PIN Management__  
Start PersoSim and select one of the provided personalizations. 
Use an eID Client to change the the PIN, try this for both reader types
 - [ ] Basic reader
 - [ ] Standard reader
   - [ ] Check AutoLogin

