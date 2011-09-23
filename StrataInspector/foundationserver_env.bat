REM Foundation Server Environment Set Up

if not DEFINED TOOLS_HOME set TOOLS_HOME=C:\Workspaces\Foundation\FoundationTools

if not DEFINED ANT_HOME set ANT_HOME=%TOOLS_HOME%\Ant\1.7.0
if not DEFINED CRUISECONTROL_HOME set CRUISECONTROL_HOME=%TOOLS_HOME%\CruiseControl\2.7.1

set Path=%Path%;%ANT_HOME%\bin;%CRUISECONTROL_HOME%
