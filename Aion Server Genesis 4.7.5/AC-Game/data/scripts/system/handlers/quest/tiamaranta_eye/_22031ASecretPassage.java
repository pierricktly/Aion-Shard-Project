/**
 * This file is part of Aion-Lightning <aion-lightning.org>.
 *
 *  Aion-Lightning is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Aion-Lightning is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details. *
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Aion-Lightning.
 *  If not, see <http://www.gnu.org/licenses/>.
 *
 * 
 * Credits goes to all Open Source Core Developer Groups listed below
 * Please do not change here something, regarding the developer credits, except the "developed by XXXX".
 * Even if you edit a lot of files in this source, you still have no rights to call it as "your Core".
 * Everybody knows that this Emulator Core was developed by Aion Lightning 
 * @-Aion-Unique-
 * @-Aion-Lightning
 * @Aion-Engine
 * @Aion-Extreme
 * @Aion-NextGen
 * @Aion-Core Dev.
 */
package quest.tiamaranta_eye;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.HandlerResult;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.model.DialogAction;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;

/**
 * @author Cheatkiller
 *
 */
public class _22031ASecretPassage extends QuestHandler {

    private final static int questId = 22031;

    public _22031ASecretPassage() {
        super(questId);
    }

    @Override
    public void register() {
        qe.registerQuestItem(182212601, questId);
        qe.registerQuestNpc(205959).addOnQuestStart(questId);
        qe.registerQuestNpc(205959).addOnTalkEvent(questId);
        qe.registerQuestNpc(206233).addOnAtDistanceEvent(questId);
    }

    @Override
    public boolean onDialogEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        int targetId = env.getTargetId();
        DialogAction dialog = env.getDialog();

        if (qs == null || qs.getStatus() == QuestStatus.NONE) {
            if (targetId == 205959) {
                if (dialog == DialogAction.QUEST_SELECT) {
                    return sendQuestDialog(env, 4762);
                } else {
                    return sendQuestStartDialog(env, 182212601, 1);
                }
            }
        } else if (qs.getStatus() == QuestStatus.REWARD) {
            if (targetId == 205959) {
                if (dialog == DialogAction.USE_OBJECT) {
                    return sendQuestDialog(env, 10002);
                } else {
                    return sendQuestEndDialog(env);
                }
            }
        }
        return false;
    }

    @Override
    public HandlerResult onItemUseEvent(QuestEnv env, Item item) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            QuestService.addNewSpawn(player.getWorldId(), player.getInstanceId(), 701416, 223.76f, 915.2f, 1183.85f, (byte) 0, 2);
            QuestService.addNewSpawn(player.getWorldId(), player.getInstanceId(), 701416, 218.97f, 894.64f, 1183.85f, (byte) 0, 2);
            QuestService.addNewSpawn(player.getWorldId(), player.getInstanceId(), 701416, 215.81f, 870.35f, 1188.6f, (byte) 0, 2);
            QuestService.addNewSpawn(player.getWorldId(), player.getInstanceId(), 701416, 213.79f, 834.98f, 1195.5f, (byte) 0, 2);
            QuestService.addNewSpawn(player.getWorldId(), player.getInstanceId(), 701416, 211.4f, 800.1f, 1200.1f, (byte) 0, 2);
            QuestService.addNewSpawn(player.getWorldId(), player.getInstanceId(), 701416, 175.82f, 773.598f, 1200.1f, (byte) 0, 2);
            QuestService.addNewSpawn(player.getWorldId(), player.getInstanceId(), 701416, 140.43f, 770.06f, 1204.17f, (byte) 0, 2);
            QuestService.addNewSpawn(player.getWorldId(), player.getInstanceId(), 206233, 123.87f, 768.8279f, 1205.9581f, (byte) 0, 2);
            return HandlerResult.fromBoolean(useQuestItem(env, item, 0, 1, false));
        }
        return HandlerResult.FAILED;
    }

    @Override
    public boolean onAtDistanceEvent(QuestEnv env) {
        Player player = env.getPlayer();
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (qs != null && qs.getStatus() == QuestStatus.START) {
            int var = qs.getQuestVarById(0);
            if (var == 1) {
                changeQuestStep(env, 1, 1, true);
            }
            return true;

        }
        return false;
    }
}
