class RemoveArtistsFromSongs < ActiveRecord::Migration[5.2]
  def change
    remove_reference :songs, :artists, foreign_key: true
  end
end
