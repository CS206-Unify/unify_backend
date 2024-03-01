package com.cs206.g2t2.models.brawlStarsAPI.battleLog;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

@Data
@EqualsAndHashCode(callSuper = true)
public class Team extends ArrayList<TeamMember> {}
