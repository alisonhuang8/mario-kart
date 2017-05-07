// This entire file is part of my masterpiece.
// Alison Huang

/**
 * This is the class used to assign types to each weapon object made. This allows them all to be stored in the
 * same composition map.
 */


package newengine.sprite.weapons;

public class WeaponType<T> {

	private String typename;
	
	public WeaponType(String typeName) {
		this.typename = typeName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((typename == null) ? 0 : typename.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		WeaponType other = (WeaponType) obj;
		if (typename == null) {
			if (other.typename != null)
				return false;
		} else if (!typename.equals(other.typename))
			return false;
		return true;
	}
	
	public String getType() {
		return typename;
	}
	
}
