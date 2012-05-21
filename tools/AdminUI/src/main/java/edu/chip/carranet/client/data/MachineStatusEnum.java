package edu.chip.carranet.client.data;


/**
 * @Author David Ortiz
 *
 * This enum represents the status of a Carranet Node
 *
 * NotResponding - machine doesn't even respond to ping
 * Responding - machine responds to ping but can't find the endpoint (might be coming up)
 * ShrineOk - found the shrine endpoint, s'all good(hopefully)
 */
public enum MachineStatusEnum {
    NotResponding, Responding, ShrineOk
}
