import React from "react";
import { Adress } from "./Adress";

export interface Player{
    playerID: number;
    rating: number;
    name: string;
    adress: Adress;
}

interface PlayerInfoProps{
    player: Player;
}
export function PlayerInfo( { player }  : PlayerInfoProps){
    return (
        <div className="informationcolumn">
            <ul>
                <div className="informationbox">
                    <li>{name}</li>
                    <li>{player.rating}</li>
                    <li>
                        <ul>
                            <li>{player.adress.street} {player.adress.streetnumber}</li>
                            <li>{player.adress.city}, {player.adress.postalcode}</li>
                        </ul>
                    </li>
                </div>
            </ul>
        </div>
    );

}