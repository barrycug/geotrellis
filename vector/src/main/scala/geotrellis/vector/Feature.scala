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

package geotrellis.vector

import com.vividsolutions.jts.{geom => jts}
import spray.json._

case class Feature[+G <: Geometry, D](geom: G, data: D) {
  def mapGeom[T <: Geometry](f: G => T): Feature[T, D] =
    Feature(f(geom), data)

  def mapData[T](f: D => T): Feature[G, T] =
    Feature(geom, f(data))
}

object Feature {
  implicit def featureToGeometry[G <: Geometry](f: Feature[G, _]): G = f.geom  
}

object PointFeature {
  def apply[D](geom: Point, data: D): Feature[Point, D] =
    Feature(geom, data)
}

object LineFeature {
  def apply[D](geom: Line, data: D): Feature[Line, D] =
    Feature(geom, data)
}

object PolygonFeature {
  def apply[D](geom: Polygon, data: D): Feature[Polygon, D] =
    Feature(geom, data)
}

object MultiPointFeature {
  def apply[D](geom: MultiPoint, data: D): Feature[MultiPoint, D] =
    Feature(geom, data)
}

object MultiLineFeature {
  def apply[D](geom: MultiLine, data: D): Feature[MultiLine, D] =
    Feature(geom, data)
}

object MultiPolygonFeature {
  def apply[D](geom: MultiPolygon, data: D): Feature[MultiPolygon, D] =
    Feature(geom, data)
}

object GeometryCollectionFeature {
  def apply[D](geom: GeometryCollection, data: D): Feature[GeometryCollection, D] =
    Feature(geom, data)
}
