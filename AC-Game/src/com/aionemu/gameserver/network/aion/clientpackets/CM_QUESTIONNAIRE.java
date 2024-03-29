package com.aionemu.gameserver.network.aion.clientpackets;

import java.util.ArrayList;
import java.util.List;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.eventEngine.battleground.model.templates.BattleGroundTemplate;
import com.aionemu.gameserver.eventEngine.battleground.services.battleground.BattleGroundManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.PacketLoggerService;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection.State;
import com.aionemu.gameserver.services.HTMLService;

/**
 * @author xTz
 */
public class CM_QUESTIONNAIRE extends AionClientPacket {

    private int objectId;
    private int itemId;
    private int itemSize;
    private List<Integer> items;
    private String stringItemsId;

    public CM_QUESTIONNAIRE(int opcode, State state, State... restStates) {
        super(opcode, state, restStates);
    }

    /*
     * (non-Javadoc)
     * @see com.aionemu.commons.network.packet.BaseClientPacket#readImpl()
     */
    @Override
    protected void readImpl() {
        PacketLoggerService.getInstance().logPacketCM(this.getPacketName());
        objectId = readD();
        itemSize = readH();
        items = new ArrayList<Integer>();
        for (int i = 0; i < itemSize; i++) {
            itemId = readD();
            items.add(itemId);
        }
        stringItemsId = readS();
    }

    /*
     * (non-Javadoc)
     * @see com.aionemu.commons.network.packet.BaseClientPacket#runImpl()
     */
    @Override
    protected void runImpl() {
    	if (objectId > 0) {
            Player player = getConnection().getActivePlayer();

            switch (objectId) {
                // battleground registration
                case 150000001:
                    List<BattleGroundTemplate> acceptedTemplates = new ArrayList<BattleGroundTemplate>();
                    for (BattleGroundTemplate template : DataManager.BATTLEGROUND_DATA.getAllTemplates()) {
                        if (player.getLevel() < template.getJoinConditions().getRequiredLevel()) {
                            continue;
                        }
                        if (player.getCommonData().getBattleGroundPoints() < template.getJoinConditions().getRequiredBgPoints()) {
                            continue;
                        }
                        if (player.getLevel() > template.getJoinConditions().getMaxLevel()) {
                            continue;
                        }
                        if (player.getCommonData().getBattleGroundPoints() > template.getJoinConditions().getMaxBgPoints()) {
                            continue;
                        }
                        acceptedTemplates.add(template);
                    }

                    if (acceptedTemplates.isEmpty()) {
                        return;
                    }

                    int choice = Integer.parseInt(stringItemsId.replaceAll("\"", "")) - 1;
                    BattleGroundTemplate tpl = acceptedTemplates.get(choice);

                    if (tpl == null) {
                        return;
                    }

                    int tplId = tpl.getTplId();
                    BattleGroundManager.registerPlayer(player, tplId);

                    break;
                // Registering form for Observers in battleGround
                case 151000001:
                    List<BattleGroundTemplate> acceptedBattlegrounds = new ArrayList<BattleGroundTemplate>();
                    for (BattleGroundTemplate template : DataManager.BATTLEGROUND_DATA.getAllTemplates()) {
                        acceptedBattlegrounds.add(template);
                    }

                    if (acceptedBattlegrounds.isEmpty()) {
                        return;
                    }

                    int choiceObs = Integer.parseInt(stringItemsId.replaceAll("\"", "")) - 1;
                    BattleGroundTemplate BgTpl = acceptedBattlegrounds.get(choiceObs);

                    if (BgTpl == null) {
                        return;
                    }

                    int BgTplId = BgTpl.getTplId();
                    BattleGroundManager.registerPlayerObs(player, BgTplId);
                    break;
                case 152000001:
                    break;
                default:
                    HTMLService.getReward(player, objectId, items);
                    break;
            }

            HTMLService.getReward(player, objectId, items);
        }
    }
}
