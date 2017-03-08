Releasetests de.persosim.rcp
============================
This document describes validation tests that shall be performed on the final product artifacts immediately before publishing. These tests focus on overall product quality and completeness (e.g. inclusion/integration of required features). For a complete test coverage please also refer to tests defined in the according bundles.

1. [ ] __Run PersoSim__  
Run PersoSim and check the following
 - [ ] GUI started
 - [ ] About dialog reflects correct information (e.g. version number)
 - [ ] no errors reported in the console

1. [ ] __Simulate certification test__  
Start PersoSim and load `DefaultPersoGt` from file. 
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

<p style="page-break-after: always"/>

