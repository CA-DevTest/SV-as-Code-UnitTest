<?xml version="1.0" ?>

<MarInfo>
<name>${virtualService.deployedName}</name>
<type>VIRTUAL_SERVICE</type>
<projectRoot>${virtualService.serviceName}</projectRoot>
<optimized>true</optimized>
<deployInfo>
    <PrimaryAsset>${vsmLocation}</PrimaryAsset>
    <Configuration>${configLocation}</Configuration>
    <ConcurrentCapacity>1</ConcurrentCapacity>
    <ThinkTimePercent>100</ThinkTimePercent>
    <AutoRestart>true</AutoRestart>
    <StartOnDeploy>true</StartOnDeploy>
    <GroupTag>$virtualService.group</GroupTag>
</deployInfo>
</MarInfo>