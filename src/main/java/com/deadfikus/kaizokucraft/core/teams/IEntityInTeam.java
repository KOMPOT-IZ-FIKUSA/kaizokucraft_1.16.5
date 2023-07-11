package com.deadfikus.kaizokucraft.core.teams;

public interface IEntityInTeam {
    boolean isPirate();
    boolean isMarine();

    KaizokuTeamSerializable getKaizokuTeam();

    boolean isTeammate(IEntityInTeam other);

    boolean isEnemy(IEntityInTeam other);

}
