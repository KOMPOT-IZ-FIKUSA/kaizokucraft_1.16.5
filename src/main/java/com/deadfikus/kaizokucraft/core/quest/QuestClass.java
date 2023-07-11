package com.deadfikus.kaizokucraft.core.quest;

import com.deadfikus.kaizokucraft.ModMain;
import com.deadfikus.kaizokucraft.core.quest.list.GeppoQuest;

import java.util.concurrent.Callable;

public enum QuestClass {

    GEPPO(GeppoQuest.class);
    public Class<? extends QuestBase> class_;
    public static QuestClass[] values = QuestClass.values();


    QuestClass(Class<? extends QuestBase> class_) {
        this.class_ = class_;
    }

    public short getI() {
        for (short i = 0; i < values.length; i++) {
            if (values[i] == this) {
                return i;
            }
        }
        return -1;
    }

    public static QuestClass get(Class<? extends QuestBase> class_) {
        for (QuestClass out: values) {
            if (out.class_.equals(class_)) {
                return out;
            }
        }
        ModMain.logError("Unable to get QuestClass for " + class_.getName());
        return GEPPO;
    }


}
