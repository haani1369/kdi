package hello

import chisel3._
import _root_.circt.stage.ChiselStage

class Top extends Module {
  val io = IO(new Bundle {
    val clk_100mhz = Input(Clock())
    val rst        = Input(AsyncReset())
    val led        = Output(UInt(1.W))
  })

  val blinker = Module(new Hello)

  io.led := blinker.io.led;
}

object Main extends App {
  ChiselStage.emitSystemVerilogFile(
    new Top,
    args = Array("--target-dir", "vivado/generated"),
    firtoolOpts = Array("-disable-all-randomization", "-strip-debug-info",
                        "-default-layer-specialization=enable")
  )
}
