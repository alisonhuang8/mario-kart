<data.SpriteMakerModel>
  <myComponents>
    <entry>
      <newengine.sprite.component.ComponentType>
        <typename>newengine.sprite.components.Speed</typename>
      </newengine.sprite.component.ComponentType>
      <newengine.sprite.components.Speed>
        <speed>100.0</speed>
      </newengine.sprite.components.Speed>
    </entry>
    <entry>
      <newengine.sprite.component.ComponentType>
        <typename>newengine.sprite.components.Health</typename>
      </newengine.sprite.component.ComponentType>
      <newengine.sprite.components.Health>
        <health>100</health>
      </newengine.sprite.components.Health>
    </entry>
    <entry>
      <newengine.sprite.component.ComponentType>
        <typename>newengine.sprite.components.Owner</typename>
      </newengine.sprite.component.ComponentType>
      <newengine.sprite.components.Owner>
        <owner>
          <name>Jake</name>
        </owner>
      </newengine.sprite.components.Owner>
    </entry>
    <entry>
      <newengine.sprite.component.ComponentType>
        <typename>newengine.sprite.components.Images</typename>
      </newengine.sprite.component.ComponentType>
      <newengine.sprite.components.Images>
        <imageFilePath>resources/bahamut_left.png</imageFilePath>
      </newengine.sprite.components.Images>
    </entry>
    <entry>
      <newengine.sprite.component.ComponentType>
        <typename>newengine.sprite.components.PathFollower</typename>
      </newengine.sprite.component.ComponentType>
      <newengine.sprite.components.PathFollower>
        <path>
          <myPath class="linked-list">
            <commons.point.GamePoint>
              <x>0.5833333333333334</x>
              <y>0.9166666666666666</y>
            </commons.point.GamePoint>
            <commons.point.GamePoint>
              <x>0.5833333333333334</x>
              <y>0.75</y>
            </commons.point.GamePoint>
            <commons.point.GamePoint>
              <x>0.5833333333333334</x>
              <y>0.5833333333333334</y>
            </commons.point.GamePoint>
            <commons.point.GamePoint>
              <x>0.5833333333333334</x>
              <y>0.4166666666666667</y>
            </commons.point.GamePoint>
            <commons.point.GamePoint>
              <x>0.5833333333333334</x>
              <y>0.25</y>
            </commons.point.GamePoint>
            <commons.point.GamePoint>
              <x>0.5833333333333334</x>
              <y>0.08333333333333333</y>
            </commons.point.GamePoint>
          </myPath>
          <pathName>First test path</pathName>
        </path>
      </newengine.sprite.components.PathFollower>
    </entry>
    <entry>
      <newengine.sprite.component.ComponentType>
        <typename>newengine.sprite.components.EventQueue</typename>
      </newengine.sprite.component.ComponentType>
      <newengine.sprite.components.EventQueue>
        <events/>
        <eventFinished>true</eventFinished>
      </newengine.sprite.components.EventQueue>
    </entry>
    <entry>
      <newengine.sprite.component.ComponentType>
        <typename>newengine.sprite.components.Collidable</typename>
      </newengine.sprite.component.ComponentType>
      <newengine.sprite.components.Collidable>
        <boundType>IMAGE</boundType>
        <bound/>
      </newengine.sprite.components.Collidable>
    </entry>
  </myComponents>
  <myScriptMap>
    <entry>
      <null/>
      <string></string>
    </entry>
  </myScriptMap>
  <spriteName>Test monster</spriteName>
  <spriteDescription>1</spriteDescription>
  <actualComponents>
    <newengine.sprite.components.Collidable>
      <boundType>IMAGE</boundType>
      <bound/>
    </newengine.sprite.components.Collidable>
    <newengine.sprite.components.EventQueue>
      <events/>
      <eventFinished>true</eventFinished>
    </newengine.sprite.components.EventQueue>
    <newengine.sprite.components.Health>
      <health>100</health>
    </newengine.sprite.components.Health>
    <newengine.sprite.components.Images>
      <imageFilePath>resources/bahamut_left.png</imageFilePath>
    </newengine.sprite.components.Images>
    <newengine.sprite.components.Owner>
      <owner>
        <name>Jake</name>
      </owner>
    </newengine.sprite.components.Owner>
    <newengine.sprite.components.PathFollower>
      <path reference="../../../myComponents/entry[5]/newengine.sprite.components.PathFollower/path"/>
    </newengine.sprite.components.PathFollower>
    <newengine.sprite.components.Speed>
      <speed>100.0</speed>
    </newengine.sprite.components.Speed>
    <newengine.sprite.components.Collidable reference="../../myComponents/entry[7]/newengine.sprite.components.Collidable"/>
    <newengine.sprite.components.EventQueue reference="../../myComponents/entry[6]/newengine.sprite.components.EventQueue"/>
    <newengine.sprite.components.Health reference="../../myComponents/entry[2]/newengine.sprite.components.Health"/>
    <newengine.sprite.components.Images reference="../../myComponents/entry[4]/newengine.sprite.components.Images"/>
    <newengine.sprite.components.Owner reference="../../myComponents/entry[3]/newengine.sprite.components.Owner"/>
    <newengine.sprite.components.PathFollower reference="../../myComponents/entry[5]/newengine.sprite.components.PathFollower"/>
    <newengine.sprite.components.Speed reference="../../myComponents/entry/newengine.sprite.components.Speed"/>
  </actualComponents>
  <skills/>
</data.SpriteMakerModel>