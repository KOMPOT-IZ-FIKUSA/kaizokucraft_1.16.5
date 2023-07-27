package com.deadfikus.kaizokucraft.core.impact;

public interface IImpactObject {

    boolean isImpactLogicImplemented(IImpactObject other);
    boolean isImpactGroupLogicImplemented(IImpactObject other);

    void performImpact(IImpactObject other);
    void performGroupImpact(IImpactObject other);

    int priority();

}
