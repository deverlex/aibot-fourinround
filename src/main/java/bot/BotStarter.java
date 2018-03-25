/*
 * Copyright 2016 riddles.io (developers@riddles.io)
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 *     For the full copyright and license information, please view the LICENSE
 *     file that was distributed with this source code.
 */

package bot;

import ai.IPoint;
import ai.IProcessing;
import ai.ITables;
import field.Field;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * bot.BotStarter
 * <p>
 * Magic happens here. You should edit this file, or more specifically
 * the doMove() method to make your bot do more than random moves.
 *
 * @author Jim van Eeden <jim@riddles.io>, Joost de Meij <joost@riddles.io>
 */

public class BotStarter {

    /**
     * Makes a turn. Edit this method to make your bot smarter.
     *
     * @return The column where the turn was made.
     */

    public int doMove(BotState state) {
        Field field = state.getField();
        return IProcessing.getInstance().doProcessing(field);

//        // make intelligent table
//        ITables iTables = ITables.build()
//                .setSize(field.getWidth(), field.getHeight())
//                .setMyId(String.valueOf(field.getMyId()))
//                .updateTable(field.getField());
//
//        // nuoc nguy hiem nhat cua doi thu
//        List<IPoint> myIPoints = new ArrayList<>();
//        myIPoints.addAll(iTables.getDangerPoints());
//
//        iTables.swapIds();
//        // tim nguy hiem nhay cua minh
//        List<IPoint> botIPoints  = new ArrayList<>();
//        botIPoints.addAll(iTables.getDangerPoints());
//
//        if (myIPoints.size() > 0 && botIPoints.size() > 0) {
//            if (myIPoints.get(0).getDanger() > botIPoints.get(0).getDanger()) {
//                return myIPoints.get(0).getX();
//            }
//            return botIPoints.get(0).getX();
//        } else if (myIPoints.size() > 0) {
//            return myIPoints.get(0).getX();
//        } else if (botIPoints.size() > 0) {
//            return botIPoints.get(0).getX();
//        }
//
//        return iTables.getCanHitPoints().get(iTables.getCanHitPoints().size() / 2).getX();

    }

    public static void main(String[] args) {
        BotParser parser = new BotParser(new BotStarter());
        parser.run();
    }
}
