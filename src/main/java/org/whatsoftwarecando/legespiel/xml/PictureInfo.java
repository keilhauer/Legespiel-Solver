package org.whatsoftwarecando.legespiel.xml;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class PictureInfo {

	private byte id;
	private String name;
	private Set<Byte> matchSet;

	public PictureInfo(byte id) {
		this(id, "", new HashSet<Byte>());
	}

	private PictureInfo(byte id, String name, Set<Byte> matchSet) {
		super();
		this.id = id;
		this.name = name;
		this.matchSet = matchSet;
	}

	public byte getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Byte> getMatchSet() {
		return matchSet;
	}

	public void setMatchSet(Set<Byte> matchSet) {
		this.matchSet = matchSet;
	}

	public void setMatchSet(String matchSetAsString) {
		this.matchSet = new HashSet<>();
		StringTokenizer st = new StringTokenizer(matchSetAsString, ",", false);
		while (st.hasMoreElements()) {
			String currentIdStr = st.nextToken();
			currentIdStr = currentIdStr.trim();
			if (!currentIdStr.isEmpty()) {
				Byte currentId = Byte.valueOf(currentIdStr);
				matchSet.add(currentId);
			}
		}
	}

	public String toString() {
		return id + ": " + name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		PictureInfo other = (PictureInfo) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
