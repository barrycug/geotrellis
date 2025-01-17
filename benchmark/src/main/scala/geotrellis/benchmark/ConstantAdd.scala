/*
 * Copyright (c) 2014 Azavea.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package geotrellis.benchmark

import geotrellis.raster._
import geotrellis.engine._
import geotrellis.engine.op.local._
import geotrellis.raster.op.local._

import com.google.caliper.Param

object ConstantAdd extends BenchmarkRunner(classOf[ConstantAdd])
class ConstantAdd extends OperationBenchmark {
  @Param(Array("bit", "byte", "short", "int", "float", "double"))
  var cellType = ""

  val layers = 
    Map(
      ("bit", "wm_DevelopedLand"),
      ("byte", "SBN_car_share"),
      ("short", "travelshed-int16"),
      ("int", "travelshed-int32"),
      ("float", "aspect"), 
      ("double", "aspect-double")
    )

  @Param(Array("128", "256", "512"))
  var size: Int = 0

  var op: Op[Tile] = null
  var source: RasterSource = null

  override def setUp() {
    val id = layers(cellType)
    val re =  getRasterExtent(id, size, size)
    op = Add(RasterSource(id, re).get, 13)
    source = 13 +: RasterSource(RasterSource(id, re).get, re.extent)
  }

  def timeConstantAddOp(reps: Int) = run(reps)(constantAddOp)
  def constantAddOp = get(source)

  def timeConstantAddSource(reps: Int) = run(reps)(constantAddSource)
  def constantAddSource = get(source)
}
