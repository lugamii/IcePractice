package de.kontux.icepractice.configs.defaults;

import de.kontux.icepractice.configs.files.MessageConfig;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigDefaults {
  private final FileConfiguration config;
  
  public ConfigDefaults(FileConfiguration config) {
    this.config = config;
  }
  
  public void provideDefaults() {
    setConfigValueDefaults();
    setScoreBoardDefaults();
    setMessageDefaults();
  }
  
  private void setConfigValueDefaults() {
    this.config.addDefault("config.events.allow-password-protection", Boolean.valueOf(true));
    this.config.addDefault("config.colours.primary", "DARK_AQUA");
    this.config.addDefault("config.colours.secondary", "YELLOW");
    this.config.addDefault("config.match.max-elo-change", Integer.valueOf(25));
    this.config.addDefault("config.use-chat-formatting", Boolean.valueOf(false));
    this.config.addDefault("config.use-chat-formatting", Boolean.valueOf(false));
    this.config.addDefault("config.queue-leave-item", "REDSTONE");
    this.config.addDefault("config.queue-leave-item-name", "&cLeave Queue");
  }
  
  private void setScoreBoardDefaults() {
    this.config.addDefault("config.scoreboards.title", "PRACTICE");
    this.config.addDefault("config.scoreboards.use-default-colours", Boolean.valueOf(true));
    if (!this.config.isConfigurationSection("config.scoreboards.idle")) {
      this.config.addDefault("config.scoreboards.idle.6", "%line%");
      this.config.addDefault("config.scoreboards.idle.5", "Online: %online-players%");
      this.config.addDefault("config.scoreboards.idle.4", "In Match: %players-inmatch%");
      this.config.addDefault("config.scoreboards.idle.3", "%space%");
      this.config.addDefault("config.scoreboards.idle.2", "example.com");
      this.config.addDefault("config.scoreboards.idle.1", "%line%");
    } 
    if (!this.config.isConfigurationSection("config.scoreboards.duel")) {
      this.config.addDefault("config.scoreboards.duel.7", "%line%");
      this.config.addDefault("config.scoreboards.duel.6", "Your ping: %own-ping%");
      this.config.addDefault("config.scoreboards.duel.5", "Their ping: %opponent-ping%");
      this.config.addDefault("config.scoreboards.duel.4", "Duration: %duration%");
      this.config.addDefault("config.scoreboards.duel.3", "%space%");
      this.config.addDefault("config.scoreboards.duel.2", "example.com");
      this.config.addDefault("config.scoreboards.duel.1", "%line%");
    } 
    if (!this.config.isConfigurationSection("config.scoreboards.sumosoloevent")) {
      this.config.addDefault("config.scoreboards.sumosoloevent.9", "%line%");
      this.config.addDefault("config.scoreboards.sumosoloevent.8", "%player1%");
      this.config.addDefault("config.scoreboards.sumosoloevent.7", "vs.");
      this.config.addDefault("config.scoreboards.sumosoloevent.6", "%player2%");
      this.config.addDefault("config.scoreboards.sumosoloevent.5", "%space%");
      this.config.addDefault("config.scoreboards.sumosoloevent.4", "Players left: %participants%");
      this.config.addDefault("config.scoreboards.sumosoloevent.3", "%space%");
      this.config.addDefault("config.scoreboards.sumosoloevent.2", "example.com");
      this.config.addDefault("config.scoreboards.sumosoloevent.1", "%line%");
    } 
    if (!this.config.isConfigurationSection("config.scoreboards.sumoteamevent")) {
      this.config.addDefault("config.scoreboards.sumoteamevent.9", "%line%");
      this.config.addDefault("config.scoreboards.sumoteamevent.8", "%player1%");
      this.config.addDefault("config.scoreboards.sumoteamevent.7", "vs.");
      this.config.addDefault("config.scoreboards.sumoteamevent.6", "%player2%");
      this.config.addDefault("config.scoreboards.sumoteamevent.5", "%space%");
      this.config.addDefault("config.scoreboards.sumoteamevent.4", "Players left: %players-left%");
      this.config.addDefault("config.scoreboards.sumoteamevent.3", "%space%");
      this.config.addDefault("config.scoreboards.sumoteamevent.2", "example.com");
      this.config.addDefault("config.scoreboards.sumoteamevent.1", "%line%");
    } 
    if (!this.config.isConfigurationSection("config.scoreboards.startingevent")) {
      this.config.addDefault("config.scoreboards.startingevent.8", "%line%");
      this.config.addDefault("config.scoreboards.startingevent.7", "Starting in: %time-left%");
      this.config.addDefault("config.scoreboards.startingevent.6", "%space%");
      this.config.addDefault("config.scoreboards.startingevent.5", "Players: %participants%");
      this.config.addDefault("config.scoreboards.startingevent.4", "Kit: %kit%");
      this.config.addDefault("config.scoreboards.startingevent.3", "%space%");
      this.config.addDefault("config.scoreboards.startingevent.2", "%line%");
      this.config.addDefault("config.scoreboards.startingevent.1", "%line%");
    } 
    if (!this.config.isConfigurationSection("config.scoreboards.party")) {
      this.config.addDefault("config.scoreboards.party.7", "%line%");
      this.config.addDefault("config.scoreboards.party.6", "%party-leader%'s party");
      this.config.addDefault("config.scoreboards.party.5", "%space%");
      this.config.addDefault("config.scoreboards.party.4", "Members: %party-members%");
      this.config.addDefault("config.scoreboards.party.3", "%space%");
      this.config.addDefault("config.scoreboards.party.2", "example.com");
      this.config.addDefault("config.scoreboards.party.1", "%line%");
    } 
    if (!this.config.isConfigurationSection("config.scoreboards.teamfight")) {
      this.config.addDefault("config.scoreboards.teamfight.7", "%line%");
      this.config.addDefault("config.scoreboards.teamfight.6", "Your team: %own-teamsize% left");
      this.config.addDefault("config.scoreboards.teamfight.5", "Their team: %opponent-teamsize% left");
      this.config.addDefault("config.scoreboards.teamfight.4", "%space%");
      this.config.addDefault("config.scoreboards.teamfight.3", "Duration: %duration%");
      this.config.addDefault("config.scoreboards.teamfight.2", "example.com");
      this.config.addDefault("config.scoreboards.teamfight.1", "%line%");
    } 
    if (!this.config.isConfigurationSection("config.scoreboards.ffa")) {
      this.config.addDefault("config.scoreboards.ffa.7", "%line%");
      this.config.addDefault("config.scoreboards.ffa.6", "FFA Match");
      this.config.addDefault("config.scoreboards.ffa.6", "Players left: %alive%");
      this.config.addDefault("config.scoreboards.ffa.5", "%space%");
      this.config.addDefault("config.scoreboards.ffa.3", "Duration: %duration%");
      this.config.addDefault("config.scoreboards.ffa.2", "example.com");
      this.config.addDefault("config.scoreboards.ffa.1", "%line%");
    } 
    if (!this.config.isConfigurationSection("config.scoreboards.koth")) {
      this.config.addDefault("config.scoreboards.koth.8", "%line%");
      this.config.addDefault("config.scoreboards.koth.7", "Capper: %captain%");
      this.config.addDefault("config.scoreboards.koth.6", "Your points: %own-points%");
      this.config.addDefault("config.scoreboards.koth.5", "Their points: %opponent-points%");
      this.config.addDefault("config.scoreboards.koth.4", "%space%");
      this.config.addDefault("config.scoreboards.koth.3", "Time remaining: %time-remaining%");
      this.config.addDefault("config.scoreboards.koth.2", "example.com");
      this.config.addDefault("config.scoreboards.koth.1", "%line%");
    } 
  }
  
  private void setMessageDefaults() {
    FileConfiguration messageConfig = MessageConfig.get();
    messageConfig.addDefault("messages.use-default-colours", Boolean.valueOf(true));
    messageConfig.addDefault("messages.general.no-duel-request", "%player% hasn't sent you a duel request.");
    messageConfig.addDefault("messages.general.accepted-request", "%player% hasn't sent you a duel request.");
    messageConfig.addDefault("messages.general.request", "%player% has sent you a duel request with the kit %kit%.");
    messageConfig.addDefault("messages.general.send-request", "You send a duel request to %player% with the kit §kit%.");
    messageConfig.addDefault("messages.match.cooldown", "Match starting in %cooldown%.");
    messageConfig.addDefault("messages.match.match-started", "Match has started.");
    messageConfig.addDefault("messages.match.starting-match", "Starting match against %opponent%  with the kit %kit%.");
    messageConfig.addDefault("messages.match.match-ended", "Match has ended.");
    messageConfig.addDefault("messages.match.combo-info", "This is a combo kit meaning you can hit faster.");
    messageConfig.addDefault("messages.match.now-spectating", "%player% is now spectating your match.");
    messageConfig.addDefault("messages.match.nolonger-spectating", "%player% is no longer spectating your match.");
    messageConfig.addDefault("messages.match.left-spectator", "You stopped spectating.");
    messageConfig.addDefault("messages.match.solo-spectator", "You are now spectating the match between %player1% and %player2% with the kit %kit%.");
    messageConfig.addDefault("messages.match.team-spectator", "You are now spectating the match between %player1%'s team and %player2%'team with the kit %kit%.");
    messageConfig.addDefault("messages.match.team-death", "%dead% was killed by %killer%.");
    messageConfig.addDefault("messages.match.team-starting-match", "Starting match against %opponent%'s team with the kit %kit%.");
    messageConfig.addDefault("messages.match.starting-ffa-match", "Starting FFA match with the kit %kit%.");
    messageConfig.addDefault("messages.match.ffa-spectator", "You are spectating the FFA match with the kit %kit%.");
    messageConfig.addDefault("messages.match.no-arena", "No free arena for this game mode.");
    messageConfig.addDefault("messages.queue.join-unranked", "You joined the unranked %kit% queue.");
    messageConfig.addDefault("messages.queue.join-ranked", "You joined the ranked %kit% queue. [%elo%]");
    messageConfig.addDefault("messages.queue.left-queue", "You left the queue queue.");
    messageConfig.addDefault("messages.arenas.create", "You created the arena with the name %name%.");
    messageConfig.addDefault("messages.arenas.pos1", "You set the first spawn for the arena %name%.");
    messageConfig.addDefault("messages.arenas.pos2", "You set the second spawn for the arena %name%.");
    messageConfig.addDefault("messages.arenas.delete", "You deleted the arena %name%.");
    messageConfig.addDefault("messages.arenas.center", "You set the center for the arena %name%.");
    messageConfig.addDefault("messages.arenas.sumo-true", "The arena %name% is now a Sumo arena.");
    messageConfig.addDefault("messages.arenas.sumo-false", "The arena %name% is no longer a Sumo arena.");
    messageConfig.addDefault("messages.arenas.already-exist", "An arena with this name already exists.");
    messageConfig.addDefault("messages.arenas.spleef-false", "The arena %name% is no longer a Spleef arena.");
    messageConfig.addDefault("messages.arenas.spleef-true", "The arena %name% is now a Spleef arena.");
    messageConfig.addDefault("messages.arenas.hcf-true", "The arena %name% is now an HCF arena.");
    messageConfig.addDefault("messages.arenas.hcf-false", "The arena %name% is no longer an HCF arena.");
    messageConfig.addDefault("messages.arenas.build-true", "The arena %name% is now a Build arena.");
    messageConfig.addDefault("messages.arenas.build-false", "The arena %name% is no longer a Build arena.");
    messageConfig.addDefault("messages.kits.notexist", "This kit doesn't exist.");
    messageConfig.addDefault("messages.kits.create", "You created a new kit with the name %kit%.");
    messageConfig.addDefault("messages.kits.setinv", "You set the inventory of the kit %kit% to your current one.");
    messageConfig.addDefault("messages.kits.delete", "You deleted the kit %kit%.");
    messageConfig.addDefault("messages.kits.sumo-true", "The kit %kit% is now a Sumo kit.");
    messageConfig.addDefault("messages.kits.sumo-false", "The kit %kit% is no longer a Sumo kit.");
    messageConfig.addDefault("messages.kits.combo-true", "The kit %kit% is now a Combo kit.");
    messageConfig.addDefault("messages.kits.combo-false", "The kit %kit% is no longer a Combo kit.");
    messageConfig.addDefault("messages.kits.give-kit", "Giving you the kit %kit%.");
    messageConfig.addDefault("messages.kits.ranked-true", "The kit %kit% is now a ranked kit.");
    messageConfig.addDefault("messages.kits.ranked-false", "The kit %kit% is no longer a ranked kit.");
    messageConfig.addDefault("messages.kits.icon-change", "You changed the icon of the kit %kit% to %item%.");
    messageConfig.addDefault("messages.kits.cooldown", "You added a %cooldown% second cooldown to this item.");
    messageConfig.addDefault("messages.events.no-permission", "You don't have the permission to host events.");
    messageConfig.addDefault("messages.events.broadcast-message", "%player% is hosting a %event%.");
    messageConfig.addDefault("messages.events.join", "%player% joined the event. (%players-left%/80)");
    messageConfig.addDefault("messages.events.leave", "%player% left the event. (%players-left%/80)");
    messageConfig.addDefault("messages.events.eliminated", "%loser% was eliminated by %winner%. (%players-left%/80)");
    messageConfig.addDefault("messages.events.password", "This event is password protected. Please enter the password in chat:");
    messageConfig.addDefault("messages.events.expireCooldown", "The event has started.");
    messageConfig.addDefault("messages.events.not-enough-players", "There were not enough players to expireCooldown the event.");
    messageConfig.addDefault("messages.events.wrong-password", "Wrong password!");
    messageConfig.addDefault("messages.events.event-full", "The event is already full.");
    messageConfig.addDefault("messages.events.winner-broadcast", "%player% won the %event%. GG!");
    messageConfig.addDefault("messages.events.starting-match", "Starting match between %player1% and %player2%.");
    messageConfig.addDefault("messages.events.host-message", "You started a %event% with the id %id%.");
    messageConfig.addDefault("messages.party.create", "You created a party.");
    messageConfig.addDefault("messages.party.open", "Everyone can join the party now.");
    messageConfig.addDefault("messages.party.close", "Only invited players can join the party now.");
    messageConfig.addDefault("messages.party.invited", "You invited %player% to your party.");
    messageConfig.addDefault("messages.party.invite-request", "%player% invited you to their party.");
    messageConfig.addDefault("messages.party.join", "%player% joined the party.");
    messageConfig.addDefault("messages.party.left", "%player% left the party.");
    messageConfig.addDefault("messages.party.now-leader", "%player% is not the party leader.");
    messageConfig.addDefault("messages.party.kicked", "%player% was kicked from the party.");
    messageConfig.addDefault("messages.party.only-leader", "Only the party leader can do that.");
    messageConfig.addDefault("messages.party.not-enough-players", "There are not enough players in your party.");
    messageConfig.addDefault("messages.party.not-invited", "You were not invited to this party.");
    messageConfig.addDefault("messages.party.no-party", "This player is currently not hosting a party.");
    messageConfig.addDefault("messages.party.sent-request", "Your party has sent %leader%'s party a duel request with the kit %kit%.");
    messageConfig.addDefault("messages.party.received-request", "%leader%'s party has sent your party a duel request with the kit %kit%.");
    messageConfig.addDefault("messages.editor.not-setup", "The kit editor was not set up yet.");
    messageConfig.addDefault("messages.editor.set-name-prompt", "Please enter the custom name:");
    messageConfig.addDefault("messages.editor.set-name", "You changed the kit's name to %name%.");
    messageConfig.addDefault("messages.editor.save-kit", "You saved the custom kit %kit%.");
    messageConfig.addDefault("messages.editor.delete-kit", "You deleted the custom kit %kit%.");
  }
}
